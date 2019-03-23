package com.mypackage

import sangria.macros.derive._
import sangria.schema._
import com.mypackage.Domain._

object GraphQLSchema {

  val IdentifiableType = InterfaceType(
    "Identifiable",
    "Entity that can be identified",

    fields[RequestContext, Identifiable](
      Field("id", StringType, resolve = _.value.id)))

  val PictureType =
    deriveObjectType[Unit, Picture](
      ObjectTypeDescription("The product picture"),
      DocumentField("url", "Picture CDN URL"))

  val ProductType =
    deriveObjectType[RequestContext, Product](
      Interfaces(IdentifiableType),
      AddFields(
        Field("pictures", ListType(PictureType),
          arguments = Argument("size", IntType) :: Nil,
          resolve = c => c.ctx.pictureRepo.picturesByProduct(c.value.id))
      )
    )

  val Id = Argument("id", StringType)

  val QueryType = ObjectType("Query", fields[RequestContext, Any](
    Field("product", OptionType(ProductType),
      description = Some("Returns a product with specific `id`."),
      arguments = Id :: Nil,
      resolve = c ⇒ c.ctx.productRepo.product(c arg Id)),

    Field("products", ListType(ProductType),
      description = Some("Returns a list of all available products."),
      resolve = _.ctx.productRepo.products)))

  val NameArg = Argument("name", StringType)
  val DescriptionArg = Argument("description", StringType)

  val MutationType = ObjectType("Mutation",
    fields[RequestContext, Any](
      Field("addProduct", ProductType,
        arguments = NameArg :: DescriptionArg :: Nil,
        resolve = req => req.ctx.productRepo.addProduct(req.arg(NameArg), req.arg(DescriptionArg))
      )
    )
  )

  val schema = Schema(QueryType, Some(MutationType))

}

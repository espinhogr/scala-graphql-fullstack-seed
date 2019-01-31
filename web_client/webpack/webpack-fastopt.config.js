// Webpack-merge is used to merge this configuration with the core one
var merge = require('webpack-merge');
var core = require('./webpack-core.config.js')

var generatedConfig = require("./scalajs.webpack.config.js");
const entries = {};
entries[Object.keys(generatedConfig.entry)[0]] = "scalajs";

module.exports = merge(core, {
  devtool: "cheap-module-eval-source-map",
  entry: entries,
  devServer: {
    hot: true
  },
  module: {
    noParse: (content) => {
      return content.endsWith("-fastopt.js");
    },
    rules: [
      // Loader rule
      {
        test: /\-fastopt.js\$/,
        use: [ require.resolve('./fastopt-loader.js') ]
      }
    ]
  },
  output: {
    filename: '[name]-bundle.js'
  }
})
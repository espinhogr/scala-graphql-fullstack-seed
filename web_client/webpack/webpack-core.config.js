var path = require("path");
var CopyWebpackPlugin = require('copy-webpack-plugin');
var CleanWebpackPlugin = require('clean-webpack-plugin');
var webpack = require('webpack');

var pathsToClean = ['dist'];
var cleanOptions = {
    verbose: true
};

module.exports = {
  mode: "development",
  resolve: {
    alias: {
      "resources": path.resolve(__dirname, "../../../../src/main/resources"),
      "scalajs": path.resolve(__dirname, "./scalajs-entry.js")
    },
    modules: [ path.resolve(__dirname, 'node_modules') ]
  },
  module: {
    rules: [
      // Config coming from HMR webpack
      {
        test: /\.css$/,
        use: [ 'style-loader', 'css-loader' ]
      },
      // "file" loader for svg
      {
        test: /\.svg\$/,
        use: [
          {
            loader: 'file-loader',
            query: {
              name: 'static/media/[name].[hash:8].[ext]'
            }
          }
        ]
      }
    ]
  },
  plugins: [
    new CleanWebpackPlugin(pathsToClean, cleanOptions),
    new CopyWebpackPlugin([
      { from: path.resolve(__dirname, "../../../../public") }
    ]),
    new webpack.HotModuleReplacementPlugin()
  ],
  output: {
    devtoolModuleFilenameTemplate: (f) => {
      if (f.resourcePath.startsWith("http://") ||
          f.resourcePath.startsWith("https://") ||
          f.resourcePath.startsWith("file://")) {
        return f.resourcePath;
      } else {
        return "webpack://" + f.namespace + "/" + f.resourcePath;
      }
    }
  }
}
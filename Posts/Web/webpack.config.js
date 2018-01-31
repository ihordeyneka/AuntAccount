var path = require("path");
var webpack = require('webpack');
module.exports = {
  devtool: "source-map",
  context: path.resolve(__dirname, "client/scripts"),
  entry: "./startup",
  output: {
    filename: "bundle.js",
    chunkFilename: "[name].js",
    publicPath: "/client/scripts/"
  },
  resolve: {
    modules: ['other_modules', 'node_modules'],
    alias: {
      domReady: "dom_ready/dom_ready",
      jqueryRaty: "jquery_raty/jquery.raty-fa",
      i18n: "jquery_i18n/jquery.i18n",
      i18n_messagestore: "jquery_i18n/jquery.i18n.messagestore",
      i18n_fallbacks: "jquery_i18n/jquery.i18n.fallbacks",
      i18n_parser: "jquery_i18n/jquery.i18n.parser",
      i18n_emitter: "jquery_i18n/jquery.i18n.emitter",
      i18n_language: "jquery_i18n/jquery.i18n.language",
      typeahead: "bootstrap-3-typeahead",
      fileinput: "bootstrap-fileinput",
      maskedinput: "jquery.maskedinput",
      slider: "bootstrap-slider",
      bootstraptable: "bootstrap-table",
      tagsinput: "bootstrap-tagsinput"
    }
  },
  module: {
    loaders: [
      { test: /\.html$/, loader: "raw-loader" }
    ]
  },
  plugins: [
    new webpack.ProvidePlugin({
      $: "jquery",
      jQuery: "jquery",
      _: "underscore"
    })
  ]
};
var path = require("path");
var webpack = require('webpack');
var BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;

module.exports = {
  devtool: "source-map",
  context: path.resolve(__dirname, "client/scripts"),
  entry: {
    main: "./entries/startup",
    authcode: "./entries/authcode",
    notfound: "./entries/notfound"
  },
  output: {
    filename: "bundle-[name].js",
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
    rules: [
      { test: /\.html$/, use: { loader: "html-loader" }  },
      { test: /\.css/, use: [{ loader: "style-loader" }, { loader: "css-loader" }] },
      { test: /\.(gif|png|woff|woff2|eot|ttf|svg)$/, use: { loader: "url-loader?limit=100000" } }
    ]
  },
  plugins: [
    new webpack.ProvidePlugin({
      $: "jquery",
      jQuery: "jquery",
      _: "underscore"
    }),
    new BundleAnalyzerPlugin({ openAnalyzer: false }), //don't use in PROD
    //new webpack.optimize.UglifyJsPlugin({ minimize: true }) //use in PROD
  ]
};
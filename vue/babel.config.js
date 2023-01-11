module.exports = {
  presets: [
    '@vue/app'
  ],
  plugins: ["@babel/plugin-syntax-dynamic-import"],
  ignore: [/[\/\\]core-js/, /@babel[\/\\]runtime/],
  'sourceType': 'unambiguous'
};
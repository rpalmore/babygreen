module.exports = {
  configureWebpack: {
    mode: 'production',
    optimization: {
      nodeEnv: 'production',
      minimize: true,
      splitChunks: {
        chunks: 'all'
      }
    },
  }
}
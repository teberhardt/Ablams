module.exports = {
    // Change build paths to make them Maven compatible
    // see https://cli.vuejs.org/config/
    outputDir: 'target/dist',
    assetsDir: 'static',
    productionSourceMap: false,
    configureWebpack: {
        "devtool": "source-map"
    },

    devServer: {
        port: 8081,
        proxy: {
            "/api": {
                target: "http://localhost:8080",
                secure: false,
                ws: true,
                changeOrigin: true,
            }
        }
    }
};

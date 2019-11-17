module.exports = {
    // Change build paths to make them Maven compatible
    // see https://cli.vuejs.org/config/
    outputDir: 'target/dist',
    assetsDir: 'static',

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

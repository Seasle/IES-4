module.exports = {
    configureWebpack: config => {
        if (process.env.NODE_ENV === 'production') {
            const JavaScriptObfuscator = require('webpack-obfuscator');

            config.plugins.push(new JavaScriptObfuscator({ rotateStringArray: true }));
        }
    },
    chainWebpack: config => {
        config.plugin('html').tap(args => {
            args[0].title = 'Home Logic';

            return args;
        });
    }
};
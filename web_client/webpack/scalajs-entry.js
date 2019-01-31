if (process.env.NODE_ENV === "production") {
    const opt = require("./web_client-opt.js");
    opt.entrypoint.main();
    module.exports = opt;
} else {
    var exports = window;
    exports.require = require("./web_client-fastopt-entrypoint.js").require;
    window.global = window;

    const fastOpt = require("./web_client-fastopt.js");
    fastOpt.entrypoint.main()
    module.exports = fastOpt;

    if (module.hot) {
        module.hot.accept();
    }
}
## Quick Start

#### 1. Run `yarn install`

This will install both run-time project dependencies and developer tools listed
in [package.json](../package.json) file. We are moving all dependencies to npm, so there will be no bower dependencies soon.

#### 2. Run `yarn build`

This command will build the app from the source files (`/src`) into the output
`/dist` folder. Then open `dist/index.html` in your browser.

Now you can open your web app in a browser, on mobile devices and start
hacking. The page must be served from a web server, e.g. apache, nginx, WebStorm built-in web server, etc., otherwise some features may not work properly.

#### 3. Run `yarn serve`
This command will watch for changes in `/src` and recompile vue templates & scss styles on the fly.
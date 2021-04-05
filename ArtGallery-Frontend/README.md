# artgallery-frontend

> A Vue.js frontend for ArtGallery app

## Heroku Deployment Guide

1. [server.js](./server.js) ([source](https://github.com/McGill-ECSE321-Fall2019/project-group-16/blob/master/tutoringsystem-frontend/server.js)) <br>
This requires the `express` module, which you can install via `npm install --save express` <br>
You also need to change the `start` script in [package.json](./server.js) to `"start": "node server.js",`

2. Add a [Procfile](./Procfile) which navigates to this folder and runs `server.js`

3. Set heroku environment variables <br>
    ```
    heroku config:set PROCFILE=<frontend-dir>/Procfile
    heroku config:set NODE_ENV=production
    heroku config:set NPM_CONFIG_PRODUCTION=false
    ```

4. Make `dist` folder by `npm run build`, remove the `dist/` line in `.gitignore`, and commit the changes

5. Push to your heroku repo

6. Other things that could go wrong
    - You put wrong `backendUrl` `frontendUrl` in your config file
    - You did not use the `--save` option for you npm installs
    - You forgot to update your backend RESTful API

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8087
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# run unit tests
npm run unit

# run e2e tests
npm run e2e

# run all tests
npm test
```

For detailed explanation on how things work, checkout the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).

## Libraries used 
[vue-router](https://router.vuejs.org/)

[vuex](https://vuex.vuejs.org/)

[vuex-persistedstate](https://github.com/robinvdvleuten/vuex-persistedstate)

import { createApp } from "vue";
import App from "./App.vue";
import { createMemoryHistory, createRouter } from "vue-router";

import LoginPage from "./views/login-page.vue";
import RegisterPage from "./views/register-page.vue";
import HomePage from "./views/home-page.vue";

const routes = [
  { path: "/", component: HomePage },
  { path: "/login", component: LoginPage },
  { path: "/register", component: RegisterPage },
];

const router = createRouter({
  history: createMemoryHistory(),
  routes,
});
router.beforeEach((to, from, next) => {
  if (to.path === "/" && !localStorage.getItem("userId")) next("/login");
  else next();
});

createApp(App).use(router).mount("#app");

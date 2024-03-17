import { createApp } from "vue";
import App from "./App.vue";
import { createMemoryHistory, createRouter } from "vue-router";

import LoginPage from "./views/login-page.vue";
import RegisterPage from "./views/register-page.vue";
import HomePage from "./views/home-page.vue";
import RecordPage from "./views/record-page.vue";
import RetuenPage from "./views/bookReturn-page.vue";

const routes = [
  { path: "/", component: HomePage },
  { path: "/login", component: LoginPage },
  { path: "/register", component: RegisterPage },
  { path: "/record", component: RecordPage },
  { path: "/bookReturn", component: RetuenPage },
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

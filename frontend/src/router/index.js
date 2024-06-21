import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import ActivityView from "../views/ActivityView";
import ClothingView from "../views/ClothingView";
import InventoryView from "../views/InventoryView";
import SignIn from "../views/auth/SignIn.vue";
import SignUp from "../views/auth/SignUp.vue";
import AllUsersView from "../views/user/AllUsersView";
import AUserView from "../views/user/AUserView";
import auth from "@/auth";

const routes = [
  {
    path: "/signin",
    name: "SignIn",
    component: SignIn,
  },
  {
    path: "/",
    redirect: "/signin",
  },
  {
    path: "/signup",
    name: "SignUp",
    component: SignUp,
    meta: { title: "Sign Up - CoatOn" },
  },
  {
    path: "/home",
    name: "home",
    component: HomeView,
    meta: { title: "Home - CoatOn" },
  },
  {
    path: "/activity",
    name: "activity",
    component: ActivityView,
    meta: { title: "Activity - CoatOn" },
  },
  {
    path: "/clothing",
    name: "clothing",
    component: ClothingView,
    meta: { title: "Clothing - CoatOn" },
  },
  {
    path: "/inventory",
    name: "inventory",
    component: InventoryView,
    meta: { title: "Inventory - CoatOn" },
    beforeEnter: async (to, from, next) => {
      const authResult = await auth.authenticated();
      if (!authResult) {
        next("/login");
      } else {
        next();
      }
    },
  },
  {
    path: "/user/all",
    name: "alluser",
    component: AllUsersView,
    meta: { title: "All Users - CoatOn" },
    beforeEnter: async (to, from, next) => {
      const authResult = await auth.hasAnyOf("ADMIN");
      if (!authResult) {
        next("/login");
      } else {
        next();
      }
    },
  },
  {
    path: "/user/:id",
    name: "user",
    component: AUserView,
    meta: { title: "User - CoatOn" },
    beforeEnter: async (to, from, next) => {
      const isAdmin = await auth.hasAnyOf("ADMIN");
      const id = to.params.id;
      if (!isAdmin || !auth.hasIdOf(id)) {
        next("/");
      } else {
        next();
      }
    },
  },
  {
    //will route to Home view if none of the previous routes apply
    path: "/:catchAll(.*)",
    name: "SignIn",
    component: SignIn,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.meta && to.meta.title) {
    document.title = to.meta.title;
  } else {
    document.title = "CoatOn"; // Default title
  }
  next();
});

export default router;

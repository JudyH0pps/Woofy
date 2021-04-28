import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import ParentHome from "@/components/ParentHome.vue";
import ParentChildDetail from "@/components/ParentChildDetail.vue";
import { authRouters } from "@/router/auth";

Vue.use(VueRouter);

const authCheck = () => (to, from, next) => {
    // if (1 == 1) {
    //     next("/");
    // }
    next("/login");
};

const routes = [
    {
        path: "/",
        name: "Home",
        component: Home,
        beforeEnter: authCheck(),
    },
    {
        path: "/ParentHome",
        name: "ParentHome",
        component: ParentHome,
    },
    {
        path: "/ParentChildDetail",
        name: "ParentChildDetail",
        component: ParentChildDetail,
    },
    ...authRouters,
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
});

export default router;

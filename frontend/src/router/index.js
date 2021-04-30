import Vue from "vue";
import VueRouter from "vue-router";
import ParentHome from "@/components/ParentHome.vue";
import ParentChildDetail from "@/components/ParentChildDetail.vue";
import ChildHome from "@/components/ChildHome.vue";
import ChildRemittance from "@/components/ChildRemittance.vue";
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
        redirect: "/login",
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
    {
        path: "/ChildHome",
        name: "ChildHome",
        component: ChildHome,
    },
    {
        path: "/ChildRemittance",
        name: "ChildRemittance",
        component: ChildRemittance,
    },
    ...authRouters,
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
});

export default router;

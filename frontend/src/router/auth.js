import Login from "@/views/auth/Login.vue";
import Signup from "@/views/auth/Signup.vue";
const authRouters = [
    {
        path: "/login",
        name: "Login",
        component: Login,
    },
    {
        path: "/signup",
        name: "Signup",
        component: Signup,
    },
];

export { authRouters };

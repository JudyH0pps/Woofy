import Login from "@/views/auth/Login.vue";
import Signup from "@/views/auth/Signup.vue";
import ParentSignupPage from "@/views/auth/ParentSignupPage.vue";
import ChildSignupPage from "@/views/auth/ChildSignupPage.vue";
import ParentSignupDetail from "@/views/auth/ParentSignupDetail.vue";

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
  {
    path: "/parentSignupPage",
    name: "ParentSignupPage",
    component: ParentSignupPage,
  },
  {
    path: "/ChildSignupPage",
    name: "ChildSignupPage",
    component: ChildSignupPage,
  },
  {
    path: "/ParentSignupDetail",
    name: "ParentSignupDetail",
    component: ParentSignupDetail,
  },
];

export { authRouters };

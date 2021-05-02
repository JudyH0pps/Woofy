<template>
  <v-app>
    <Nav v-if="$route.name != 'Login'"></Nav>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import Nav from "@/components/Nav.vue";

export default {
  name: "App",
  components: {
    Nav,
  },

  data: () => ({
    //
  }),
};
</script>

<style>
.OK_button {
  margin: 50px 0;
  background-color: #3c3eca;
  width: 100%;
  height: 60px;
  border-radius: 8px;
  color: white;
  font-weight: bold;
  border: none;
}
</style>
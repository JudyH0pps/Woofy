<template>
  <v-app>
    <Nav v-if="$route.name!='Login'"></Nav>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import Nav from '@/components/Nav.vue'

export default {
  name: 'App',
  components:{
    Nav
  },

  data: () => ({
    //
  }),
};
</script>

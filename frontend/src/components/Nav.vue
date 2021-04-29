<template>
  <div class="nav">
    <div @click.stop="drawer = !drawer" class="hamburger">
    <v-icon size="4vh" color="#5b9ad5">mdi-menu</v-icon>
    </div>
    <div @click="moveTo('ParentHome')">
      <span class="logo"><span style="font-style:oblique ">WOORI</span> i</span>
    </div>
    <v-navigation-drawer
      v-model="drawer"
      absolute
      temporary
      width="80%"
    >
      <div class="wooridrawer">
        <div style="height:8%; width:100%; text-align:right; padding:3%;"><v-icon @click="drawer = false" color="white">mdi-close</v-icon></div>
        <div style="height:30%; width:90%; background-color:white;">
          임시
        </div>
        <div style="height:55%; width:100%; background-color:white; position:absolute; bottom:0"></div>
      </div>
    </v-navigation-drawer>
  </div>
  
</template>

<script>
export default {
data() {
    return {
      drawer: false,
      group: null,
    };
  },
  watch: {
      group () {
        this.drawer = false
      },
    },
  methods: {
    moveTo(componentName) {
      this.$router.push({ name: componentName });
    },
  },
}
</script>

<style lang="scss">
@import "@/assets/scss/app/App.scss";

.hamburger{
  position: absolute;
  left: 0;
  padding: 0 2.5vh;
}

.logo{
  color: #0078b9;
    font-size: larger;
    font-weight: 600;
}
</style>
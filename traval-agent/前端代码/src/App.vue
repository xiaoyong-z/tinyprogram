<template>
  <router-view :key="$route.path"></router-view>
</template>
<style>
    @import "./assets/css/main.css";
    /*@import "./assets/css/color-dark.css";     深色主题*/
    @import "./assets/css/theme-green/color-green.css";   /*浅绿色主题*/
</style>
<template>
  <RouterView></RouterView>

</template>

<script>
import { onMounted, reactive } from 'vue'
import { useRouter } from "vue-router";
import { useStore } from 'vuex'
export default {
  name: 'App',

  setup() {
    const router = useRouter()
    const store = useStore()
    let data = reactive({


    })


    onMounted(() => {

      

    })









    return {
      data,

    }

  }

}
</script>

<style scoped>


@media (max-width: 1224px) {}
</style>

<template>
  <AlertVue ref="alertRef"></AlertVue>
</template>

<script setup>
import { ref } from "vue";
// cookies
import { useCookies } from "vue3-cookies";
// axios
import axios from "axios";

// alert
import AlertVue from "@/components/public/alert.vue";

const alertRef = ref(null);
const { cookies } = useCookies();
const api_url = `${import.meta.env.VITE_API_HOST}/user/user_info`;


function showAlert(params) {
  if (alertRef.value) {
    alertRef.value.showAlert(params);
  }
}

async function requestJWT(data) {
  return await axios
    .post(api_url, data, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((response) => {
      if (response.status == 200 && response.data.code == 200) {
        return true;
      } else {
        return false;
      }
    })
    .catch((error) => {
      return false;
    });
}

async function inspection() {
  const jwt = cookies.get("jwt");
  const name = cookies.get("username");
  if (jwt && name) {
    const result = await requestJWT({
      name: name,
      token: jwt,
    });

    if (result) return true;
  }
  return false;
}
defineExpose({
  inspection,
  showAlert
});
</script>

<style>
</style>
<template></template>

<script setup>
// cookies
import { useCookies } from "vue3-cookies";
// axios
import axios from "axios";

// use cookies
const { cookies } = useCookies();
// axios create
const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_HOST,
  // withCredentials: true,
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
});

async function verifyJwt() {
  const jwt = cookies.get("jwt");
  if (jwt == null || jwt == undefined) {
    return false;
  }
  // console.log(jwt);
  return await apiClient
    .get("/user/verify", {
      headers: {
        "Authentication": `Bearer ${jwt}`
      }
    })
    .then(response => {
      return true;
    }).catch(error => {
      return false;
    });
}

defineExpose({
  verifyJwt
});
</script>
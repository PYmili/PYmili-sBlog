<template>
  
</template>

<script setup>
import axios from 'axios';
import { useCookies } from 'vue3-cookies';

const URL = `${import.meta.env.VITE_API_HOST}/blogs/find`;
const { cookies } = useCookies();

async function findRequest(data) {
    const jwt = cookies.get('jwt');    
    if (!jwt) {
        return undefined;
    }
    data['jwt'] = jwt;
    return await axios.post(URL, data, {
        headers: {
            "Content-Type": "application/json",
        }
    }).then((response) => {
        if (response.status == 200 && response.data.code == 200) {
            return response.data.data;
        } else {
            return undefined;
        }
    }).catch((error) => {
        return undefined;
    })
}

defineExpose({
    findRequest
});
</script>

<style>

</style>
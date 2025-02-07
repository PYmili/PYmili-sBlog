<script setup>
import axios from 'axios';
import { useCookies } from 'vue3-cookies';

const URL = `${import.meta.env.VITE_API_HOST}/blog/find`;
const { cookies } = useCookies();

async function findRequest(params) {
    // console.log(params);
    const jwt = cookies.get('jwt');    
    return await axios.get(URL + `?id=${params.id}`, {
        headers: {
            "Content-Type": "application/json",
            "Authentication": `Bearer ${jwt}`
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
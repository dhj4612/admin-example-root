import {createApp} from 'vue'
import {createPinia} from 'pinia'

import App from './App.vue'
import router from './router'
import {useUserStore} from "@/stores/user.js";

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')

app.directive('auth', {
    beforeMount(el, binding, vNode) {
        if (!useUserStore().hasAuthorities(binding.value)) {
            el.style.display = 'none'
        }
    },
})

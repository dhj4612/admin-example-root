<template>
  <a-layout>
    <a-layout-sider collapsed-width="90"
                    style="height: 100vh"
                    v-model:collapsed="collapsed"
                    :trigger="null"
                    theme="dark"
                    collapsible>
      <div class="logo" @click="router.push('/')">
        <img class="log-img" :src="elementLogo" alt="...">
        <p class="logo-text">Admin</p>
      </div>
      <a-menu v-model:selectedKeys="selectedKeys"
              @click="item =>
              item?.item?.path &&
              item.item.path !== router.currentRoute.value.meta.url &&
              router.push(item.item.path)"
              theme="dark"
              mode="inline">
        <template v-for="menu in menusStore.menus">
          <template v-if="!menu.children || !menu.children.length">
            <a-menu-item :key="menu.id" :path="menu.url">
              <template #icon>
                <DynamicIcon :type="menu.icon || 'BarsOutlined'"/>
              </template>
              <template #title>
                {{ menu.name }}
              </template>
            </a-menu-item>
          </template>

          <template v-else>
            <a-sub-menu>
              <template #icon>
                <DynamicIcon :type="menu.icon || 'BarsOutlined'"/>
              </template>
              <!--顶层菜单名称-->
              <template #title>
                {{ menu.name }}
              </template>

              <a-menu-item v-for="subMenu in menu.children" :key="subMenu.id" :path="subMenu.url">
                <DynamicIcon :type="subMenu.icon || 'BarsOutlined'"/>
                <span>{{ subMenu.name }}</span>
              </a-menu-item>
            </a-sub-menu>
          </template>
        </template>
      </a-menu>
    </a-layout-sider>

    <a-layout style="height: 100vh">
      <a-layout-header style="width: 100%;height: 50px;background: #fff;padding: 0 5px;">
        <div style="display: flex;height: 50px;">
          <div class="header-layout">
            <MenuOutlined
                style="font-size: 15px"
                v-if="collapsed"
                class="trigger"
                @click="_ => collapsed = !collapsed"/>
            <MenuUnfoldOutlined style="font-size: 15px" v-else class="trigger" @click="_ => collapsed = !collapsed"/>
          </div>

          <div class="header-layout">
          </div>
        </div>
      </a-layout-header>
      <a-layout-content class="content-box no-scroll">
        <RouterView/>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>
<script setup>
import {
  MenuOutlined,
  MenuUnfoldOutlined
} from '@ant-design/icons-vue';

import {ref} from 'vue';
import {useRouter} from "vue-router";
import elementLogo from '@/assets/element-plus-logo.svg'
import DynamicIcon from "@/components/DynamicIcon.vue";
import {useMenusStore} from "@/stores/menus.js";

const selectedKeys = ref([]);
const collapsed = ref(false);
const router = useRouter();
const menusStore = useMenusStore();
</script>

<style>
.header-layout {
  display: flex;
  height: 100%;
  align-items: center;
  flex: .5
}

.log-img {
  width: 85px;
  padding: 3px
}

.logo {
  height: 50px;
  background-color: #001529;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

.logo-text {
  color: white;
  margin-left: 5px;
  font-weight: bold;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: clip;
}

.content-box {
  overflow: scroll;
  margin: 16px;
  padding: 10px;
  background: #fff;
  min-height: 280px;
}

.no-scroll {
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE and Edge */
}

.no-scroll::-webkit-scrollbar {
  display: none; /* Chrome, Safari, and Opera */
}
</style>

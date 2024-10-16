<template>
  <a-layout>
    <a-layout-sider collapsed-width="90"
                    style="height: 100vh"
                    v-model:collapsed="collapsed"
                    :trigger="null"
                    theme="dark"
                    collapsible>
      <div class="logo">
        <img style="width: 85px;padding: 3px" :src="elementLogo" alt="...">
        <p class="logo-text">Admin</p>
      </div>
      <a-menu v-model:selectedKeys="selectedKeys" theme="dark" mode="inline">
        <a-menu-item key="1">
          <UserOutlined/>
          <span>nav 1</span>
        </a-menu-item>
        <a-menu-item key="2">
          <VideoCameraOutlined/>
          <span>nav 2</span>
        </a-menu-item>
        <a-menu-item key="3">
          <UploadOutlined/>
          <span>nav 3</span>
        </a-menu-item>
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
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
        <p>Content</p>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>
<script setup>
import {
  UserOutlined,
  MenuOutlined,
  MenuUnfoldOutlined,
  VideoCameraOutlined,
  UploadOutlined
} from '@ant-design/icons-vue';
import {ref} from 'vue';
import elementLogo from '@/assets/element-plus-logo.svg'


const selectedKeys = ref(['1']);
const collapsed = ref(false);
</script>

<style>
.header-layout {
  display: flex;
  height: 100%;
  align-items: center;
  flex: .5
}

.logo {
  height: 50px;
  background-color: #001529;
  display: flex;
  justify-content: center;
  align-items: center;
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

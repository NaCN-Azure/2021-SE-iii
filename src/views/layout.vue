<template>
    <div id="layout">
        <Header></Header>
        <transition name="fade-transform" mode="out-in">
            <router-view/>
        </transition>
    </div>
</template>
<script>
    import Header from '@/components/header'
    export default {
        name: 'layout',
        components: {
            Header
        }
    }
</script>
<style>
    body{
        margin: 0;
        padding: 0;
        overflow: hidden;
    }
</style>

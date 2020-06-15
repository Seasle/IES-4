<template>
    <div id="app">
        <header class="header">
            <Nav class="header__nav" />
        </header>
        <router-view />
    </div>
</template>

<script>
import Nav from '@/components/Nav.vue';

export default {
    name: 'App',
    components: {
        Nav
    }
};
</script>

<style lang="scss">
:root {
    --basic-black: #333333;
    --basic-white: #ffffff;
    --basic-gray: #f5f5f5;
    --special-color: #1297e4;
    --special-color-alpha: rgba(18, 151, 228, 0.25);
}

body {
    margin: 0;
    font: 16px 'Roboto', sans-serif;
    overflow: hidden;
}

#app {
    height: 100vh;
    display: grid;
    grid-template-rows: auto 1fr;
}

.header {
    background: var(--basic-black);
    color: var(--basic-white);

    &__nav {
        height: 100%;
    }
}

.content {
    padding: 0 8px;
    border-width: 8px 8px 8px 0;
    border-style: solid;
    border-color: transparent;
    display: flex;
    flex-direction: column;
    background: #f5f5f5;
    overflow-y: auto;

    &::-webkit-scrollbar {
        width: 8px;
        height: 8px;

        &-thumb {
            border-radius: 4px;
            background: var(--basic-black);
        }
    }
}

strong {
    font-weight: 500;
}
</style>
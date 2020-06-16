<template>
    <div id="app">
        <header class="header">
            <Nav class="header__nav" />
        </header>
        <div class="page">
            <transition name="translate">
                <router-view />
            </transition>
        </div>
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
    background: #f5f5f5;
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

.page {
    position: relative;
    display: flex;
    overflow: hidden;
}

.content {
    margin: 8px 0;
    padding: 0 16px 0 24px;
    gap: 16px;
    border-right: 8px solid transparent;
    flex: 1 1 auto;
    display: grid;
    grid-auto-rows: max-content;
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

.wrapper {
    gap: 16px;
    display: grid;
    grid-auto-rows: max-content;
}

strong {
    font-weight: 500;
}

.translate-enter-active,
.translate-leave-active {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    animation-duration: 0.5s;
    animation-fill-mode: forwards;
    animation-timing-function: ease-in-out;
}
.translate-leave-to {
    animation-name: slide-out;
}

.translate-enter-to {
    animation-name: slide-in;
}

@keyframes slide-out {
    from {
        transform: translateX(0%);
    }

    to {
        transform: translateX(-100%);
    }
}

@keyframes slide-in {
    from {
        transform: translateX(100%);
    }

    to {
        transform: translateX(0%);
    }
}
</style>
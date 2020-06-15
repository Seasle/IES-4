<template>
    <router-link class="nav__link" exact-active-class="nav__link--current" :to="to">
        <slot />
    </router-link>
</template>

<script>
export default {
    name: 'NavLink',
    props: {
        to: {
            type: String,
            required: true
        }
    }
};
</script>

<style lang="scss" scoped>
.nav__link {
    padding: 8px 16px;
    column-gap: 8px;
    display: grid;
    grid-auto-flow: column;
    grid-auto-columns: max-content;
    align-items: center;
    text-decoration: none;
    color: inherit;

    @media (min-width: 880px) {
        border-radius: 4px 4px 0 0;
    }

    &:not(&--current) {
        &:hover {
            background: rgba(255, 255, 255, 0.1);
        }
    }

    &--current {
        font-weight: 700;
        background: var(--basic-gray);
        color: var(--basic-black);
    }

    &:focus {
        text-decoration: underline;
        outline: none;
    }
}
</style>
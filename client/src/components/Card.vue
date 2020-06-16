<template>
    <div
        :class="['card', { 'card--current': selected }]"
        tabindex="0"
        @click.prevent="clickHandle"
        @keypress.enter.prevent="clickHandle"
    >
        <slot />
    </div>
</template>

<script>
export default {
    name: 'Card',
    props: {
        selected: {
            type: Boolean
        }
    },
    methods: {
        clickHandle() {
            this.$emit('select');
        }
    }
};
</script>

<style lang="scss" scoped>
.card {
    --shadow-color: rgba(0, 0, 0, 0.05);
    --drop-shadow: 0 1px  1px  0 var(--shadow-color),
                   0 2px  2px  0 var(--shadow-color),
                   0 4px  4px  0 var(--shadow-color),
                   0 8px  8px  0 var(--shadow-color),
                   0 16px 16px 0 var(--shadow-color);

    padding: 16px;
    border-radius: 4px;
    background: #ffffff;
    box-shadow: inset 0 0 0 0 transparent, var(--drop-shadow);
    transition: box-shadow 0.2s ease-in-out;
    cursor: pointer;
    z-index: 2;

    &--current {
        grid-row: 1;
        grid-column: 1 / -1;
        font-size: 18px;
        z-index: 1;
    }

    &:focus {
        box-shadow: inset 0 0 0 4px var(--special-color), var(--drop-shadow);
        outline: none;
    }

    &:hover {
        --shadow-color: var(--special-color-alpha);
    }
}
</style>
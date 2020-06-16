<template>
    <ul class="list">
        <li
            class="list__item"
            v-for="(item, index) in items"
            :key="index"
        >
            <slot :item="item" />
        </li>
    </ul>
</template>

<script>
export default {
    name: 'List',
    props: {
        items: {
            type: Array,
            required: true
        }
    }
};
</script>

<style lang="scss" scoped>
.list {
    margin: 0;
    padding: 0;
    list-style: none;

    &__item {
        line-height: 1.5;
    }
}
</style>
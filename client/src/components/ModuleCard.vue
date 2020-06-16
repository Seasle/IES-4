<template>
    <Card
        class="wrapper"
        :selected="isCurrent"
        @select="pickModule"
    >
        <h1 class="wrapper__title">{{ name }}</h1>
        <List :items="module.description">
            <template v-slot="{ item }">
                <strong>{{ item.key }}:</strong> {{ item.value }}
            </template>
        </List>
    </Card>
</template>

<script>
import Card from '@/components/Card.vue';
import List from '@/components/List.vue';
import { MODULE_NAMES } from '@/modules/constants.js';

export default {
    name: 'ModuleCard',
    props: {
        module: {
            type: Object,
            required: true
        }
    },
    components: {
        Card,
        List
    },
    computed: {
        isCurrent() {
            return this.$store.state.module === this.module.name;
        },
        name() {
            return MODULE_NAMES.get(this.module.name);
        }
    },
    methods: {
        pickModule() {
            this.$store.dispatch('updateModule', this.module.name);

            this.$router.push('monitoring');
        }
    }
};
</script>

<style lang="scss" scoped>
.wrapper {
    gap: 16px;
    display: grid;
    grid-auto-rows: max-content;

    &__title {
        margin: 0;
        font-weight: 500;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
}
</style>
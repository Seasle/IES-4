<template>
    <Card
        class="wrapper"
        :selected="isCurrent"
        @select="pickModule"
    >
        <div class="wrapper__body">
            <h1 class="wrapper__title">{{ name }}</h1>
            <ul class="wrapper__description-list">
                <li class="wrapper__item" v-for="(item, index) in module.description" :key="index">
                    <strong>{{ item.key }}:</strong> {{ item.value }}
                </li>
            </ul>
        </div>
    </Card>
</template>

<script>
import Card from '@/components/Card.vue';
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
        Card
    },
    computed: {
        isCurrent() {
            return false;
            // return this.$store.state.house === this.house.id;
        },
        name() {
            return MODULE_NAMES.get(this.module.name);
        }
    },
    methods: {
        pickModule() {
            this.$store.dispatch('updateModule', this.module.name);

            // this.$router.push('module-selection');
        }
    }
};
</script>

<style lang="scss" scoped>
.wrapper {
    display: grid;

    &__title {
        margin: 0;
        font-weight: 500;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    &__description-list {
        margin: 0;
        padding: 0;
        list-style: none;
    }

    &__item {
        line-height: 1.5;
    }
}
</style>
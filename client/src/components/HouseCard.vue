<template>
    <Card
        class="wrapper"
        :selected="isCurrent"
        @select="pickHouse"
    >
        <img class="wrapper__image" :src="image" alt="" aria-hidden="true">
        <div class="wrapper__body">
            <h1 class="wrapper__title">{{ house.description }}</h1>
            <List :items="items">
                <template v-slot="{ item }">
                    <strong>{{ item.label }}:</strong> {{ item.value }}
                </template>
            </List>
        </div>
    </Card>
</template>

<script>
import Card from '@/components/Card.vue';
import List from '@/components/List.vue';
import { FILES_URL } from '@/modules/constants.js';

export default {
    name: 'HouseCard',
    props: {
        house: {
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
            return this.$store.state.house === this.house.id;
        },
        image() {
            return `${FILES_URL}/img/${this.house.scheme}`;
        },
        items() {
            return [
                {
                    label: 'Площадь',
                    value: this.house.area
                },
                {
                    label: 'Количество комнат',
                    value: this.house.roomCount
                },
                {
                    label: 'Разделенный санузел',
                    value: this.house.bathroomSeparated ? 'Да' : 'Нет'
                }
            ];
        }
    },
    methods: {
        pickHouse() {
            this.$store.dispatch('updateHouse', this.house.id);
            this.$store.dispatch('updateModule', null);

            this.$router.push('module-selection');
        }
    }
};
</script>

<style lang="scss" scoped>
.wrapper {
    gap: 16px;
    display: grid;
    grid-template-columns: 240px 1fr;

    &__image {
        width: 240px;
        height: 240px;
        margin: 0 auto;
        border-radius: 4px;
        display: block;
        object-fit: cover;
    }

    &__body {
        gap: 24px;
        display: grid;
        grid-auto-rows: max-content;
    }

    &__title {
        margin: 0;
        font-weight: 500;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
}
</style>
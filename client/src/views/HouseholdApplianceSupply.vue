<template>
    <div class="wrapper">
        <div
            class="group"
            v-for="(group, index) in groups"
            :key="index"
        >
            <h2 class="group__title">{{ group }}</h2>
            <div
                class="group__item"
                v-for="(entry, index) in entries(group)"
                :key="index"
            >
                <h3 class="group__subtitle">{{ entry }}</h3>
                <div class="group__wrapper">
                    <p
                        class="group__text"
                        v-for="(message, index) in messages(group, entry)"
                        :key="index"
                    >{{ message }}</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import format from '@/modules/format.js';

export default {
    name: 'HouseholdApplianceSupply',
    props: ['data'],
    data() {
        return {
            types: {
                dishwasher: 'Посудомоечная машина',
                fridge: 'Холодильник',
                kettle: 'Чайник',
                microwave: 'Микроволновка',
                stove: 'Печь',
                washingMachine: 'Стиральная машина'
            }
        };
    },
    computed: {
        list() {
            const states = new Map();
            const types = Object.keys(this.types);

            return this.data.reduce((accumulator, entry) => {
                const date = format(new Date(entry.timestamp), '[day].[month].[year]');

                if (!accumulator.has(date)) {
                    accumulator.set(date, new Map());
                }

                const store = accumulator.get(date);

                for (const type of types) {
                    if (states.get(type) !== entry[type]) {
                        const time = format(new Date(entry.timestamp), '[hour]:[minute]');
                        if (!store.has(time)) {
                            store.set(time, []);
                        }

                        store.get(time).push(`${entry[type] ? 'Включено' : 'Выключено'}: ${this.types[type]}`);
                    }

                    states.set(type, entry[type]);
                }

                if (store.size === 0) {
                    accumulator.delete(date);
                }

                return accumulator;
            }, new Map());
        },
        groups() {
            return [...this.list.keys()];
        }
    },
    methods: {
        entries(group) {
            return [...this.list.get(group).keys()];
        },
        messages(group, entry) {
            return [...this.list.get(group).get(entry).values()];
        }
    }
};
</script>

<style lang="scss" scoped>
.group {
    display: grid;
    grid-auto-rows: max-content;
    box-shadow: inset 0 0 0 1px var(--basic-black);

    &__title {
        margin: 0;
        padding: 16px;
        background: var(--basic-black);
        color: var(--basic-white);
    }

    &__subtitle {
        margin: 0;
        padding: 16px;
        border-right: 1px solid var(--basic-black);
    }

    &__item {
        display: grid;
        grid-template-columns: 80px 1fr;

        &:not(:last-child) {
            border-bottom: 1px solid var(--basic-black);
        }
    }

    &__wrapper {
        padding: 16px;
    }

    &__text {
        margin: 0;
        line-height: 1.5;
    }
}
</style>
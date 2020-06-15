<template>
    <nav class="nav">
        <NavLink to="house-selection">
            <NavIcon src="building.svg" />
            Выбор дома
        </NavLink>
        <NavLink to="module-selection" v-if="isHousePicked">
            <NavIcon src="directions.svg" />
            Выбор модуля
        </NavLink>
        <NavLink to="calculation-of-use" v-if="isModulePicked">
            <NavIcon src="traffic-cone.svg" />
            Рассчет использования
        </NavLink>
        <NavLink to="monitoring" v-if="isModulePicked">
            <NavIcon src="electric-tower.svg" />
            Мониторинг
        </NavLink>
    </nav>
</template>

<script>
import NavLink from '@/components/NavLink.vue';
import NavIcon from '@/components/NavIcon.vue';

export default {
    name: 'Nav',
    components: {
        NavLink,
        NavIcon
    },
    computed: {
        isHousePicked() {
            return this.$store.state.house !== null;
        },
        isModulePicked() {
            return this.$store.state.module !== null;
        }
    }
};
</script>

<style lang="scss" scoped>
.nav {
    padding: 4px;
    gap: 4px;
    display: grid;
    grid-auto-flow: column;
    grid-auto-columns: max-content;

    @media (max-width: 880px) {
        grid-auto-flow: row;
    }

    @media (min-width: 880px) {
        padding-bottom: 0;
    }
}
</style>
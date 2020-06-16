<template>
    <div class="wrapper">
        <Chart
            :title="'Энергопотребление'"
            :type="'line'"
            :labels="labels"
            :data="energyConsumption"
        />
    </div>
</template>

<script>
import Chart from '@/components/Chart.vue';
import format from '@/modules/format.js';

export default {
    name: 'PowerSupply',
    props: ['data'],
    components: {
        Chart
    },
    computed: {
        labels() {
            return this.data.map(entry => format(new Date(entry.timestamp), '[day].[month].[year]'));
        },
        energyConsumption() {
            return this.data.map(entry => entry.energyConsumption);
        }
    }
};
</script>
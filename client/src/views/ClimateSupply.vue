<template>
    <div class="wrapper">
        <Chart
            :title="'Температура'"
            :type="'line'"
            :labels="labels"
            :data="temperature"
        />
        <Chart
            :title="'Влажность'"
            :type="'line'"
            :labels="labels"
            :data="humidity"
            :options="humidityOptions"
        />
    </div>
</template>

<script>
import Chart from '@/components/Chart.vue';
import format from '@/modules/format.js';

export default {
    name: 'ClimateSupply',
    props: ['data'],
    components: {
        Chart
    },
    data() {
        return {
            humidityOptions: {
                scales: {
                    yAxes: [
                        {
                            display: true,
                            ticks: {
                                suggestedMin: 0,
                                suggestedMax: 100
                            }
                        }
                    ]
                }
            }
        };
    },
    computed: {
        labels() {
            return this.data.map(entry => format(new Date(entry.timestamp), '[day].[month].[year]'));
        },
        temperature() {
            return this.data.map(entry => entry.temperature);
        },
        humidity() {
            return this.data.map(entry => entry.humidity);
        }
    }
};
</script>
<template>
    <div class="chart">
        <h2 class="chart__title">{{ title }}</h2>
        <div class="chart__container">
            <canvas ref="canvas"></canvas>
        </div>
    </div>
</template>

<script>
import Chart from 'chart.js';

export default {
    name: 'Chart',
    props: {
        title: {
            type: String,
            required: true
        },
        type: {
            type: String,
            required: true
        },
        labels: {
            type: Array,
            required: true
        },
        data: {
            type: Array,
            required: true
        },
        options: {
            type: Object,
            required: false
        }
    },
    watch: {
        data() {
            this.update();
        }
    },
    data() {
        return {
            chart: null
        };
    },
    methods: {
        update() {
            if (this.chart !== null) {
                this.chart.data.labels = this.labels;
                this.chart.data.datasets[0].data = this.data;

                this.chart.update();
            }
        }
    },
    mounted() {
        this.chart = new Chart(this.$refs.canvas, {
            type: this.type,
            data: {
                labels: this.labels,
                datasets: [
                    {
                        data: this.data,
                        borderColor: 'rgb(148, 176, 219)',
                        backgroundColor: 'rgba(148, 176, 219, 0.5)'
                    }
                ]
            },
            options: Object.assign({
                responsive: true,
                maintainAspectRatio: false,
                legend: {
                    display: false
                },
                hover: {
                    mode: 'nearest',
                    intersect: true
                }
            }, this.options || {})
        });
    },
    beforeDestroy() {
        this.chart.destroy();
    }
};
</script>

<style lang="scss" scoped>
.chart {
    height: 360px;
    padding: 20px;
    border-radius: 4px;
    display: flex;
    flex-direction: column;
    background: #ffffff;
    box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.1),
                0 2px 2px 0 rgba(0, 0, 0, 0.1),
                0 4px 4px 0 rgba(0, 0, 0, 0.1),
                0 8px 8px 0 rgba(0, 0, 0, 0.1),
                0 16px 16px 0 rgba(0, 0, 0, 0.1);

    &__title {
        margin: 0 0 20px 0;
        font-weight: 500;
    }

    &__container {
        flex: 1 1 auto;
    }
}
</style>
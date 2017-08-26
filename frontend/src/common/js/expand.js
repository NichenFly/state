import { expandWidth } from 'constants/constants'

export function expand(ExpandRow, width) {
    return {
        type: 'expand',
        width: width | expandWidth,
        render: (h, params) => {
            return h(ExpandRow, {
                props: {
                    row: params.row
                }
            })
        }
    }
}
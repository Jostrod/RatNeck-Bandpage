export const formatCurrency = (value: number, locale = 'nb-NO') => {

    return new Intl.NumberFormat(locale, {
        style: 'currency',
        currency: 'NOK'
    }).format(value)
}
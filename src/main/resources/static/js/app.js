document.querySelectorAll('.js-submit-confirm').forEach(($item) => {
    $item.addEventListener('submit', (event) => {
        if (!confirm(event.currentTarget.getAttribute('data-confirm-message'))) {
            event.preventDefault();
            return false;
        }
        return true;
    });
});
document.querySelectorAll('.js-datepicker, .js-timepicker, .js-datetimepicker').forEach(($item) => {
    const flatpickrConfig = {
        allowInput: true,
        time_24hr: true,
        enableSeconds: true,
        locale: {
            firstDayOfWeek: 1
        }
    };
    if ($item.classList.contains('js-datepicker')) {
        flatpickrConfig.dateFormat = 'd.m.Y';
    } else if ($item.classList.contains('js-timepicker')) {
        flatpickrConfig.enableTime = true;
        flatpickrConfig.noCalendar = true;
        flatpickrConfig.dateFormat = 'H:i:S';
    } else { // datetimepicker
        flatpickrConfig.enableTime = true;
        flatpickrConfig.altInput = true;
        flatpickrConfig.altFormat = 'd.m.Y H:i:S';
        flatpickrConfig.dateFormat = 'd.m.YTH:i:S';
    }
    flatpickr.localize(flatpickr.l10ns.de);
    flatpickr($item, flatpickrConfig);
});

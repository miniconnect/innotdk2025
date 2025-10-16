Neutralino.init();
Neutralino.events.on("windowClose", () => Neutralino.app.exit());

document.querySelectorAll(".screen-switcher-action").forEach(element => {
    element.addEventListener("click", e => {
        const index = element.dataset.target;
        Neutralino.os.execCommand(`xdotool set_desktop ${index}`);
    });
});

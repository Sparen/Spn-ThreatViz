//Shorthand for creating an element
//First argument is the type (a/div/etc), second argument is element's attributes/props, remaining are children elements
const rce = React.createElement;

//Website banner
const banner = rce('div', 
    {className: 'stvheader'},
    rce('h1', {}, "Sparen ThreatViz")
)

//Website Information
const subbanner = rce('div', 
    {className: 'stvctrtxt'},
    rce('p', {}, 
        "Welcome to Sparen's Threat Visualizer. Here you can access and search through a snapshot of the ",
        rce('a', {target: '_blank', href: 'https://nvd.nist.gov/vuln/data-feeds'}, "National Vulnerability Database's data feeds"),
        "."
    )
)

//Search Bar
const searchbar = rce('div', 
    {className: 'stvctrtxt'},
    rce('input',
        {type: 'text', name: 'searchbar', className: 'stvsearchbar', placeholder: 'Input Search Query here (space-delimited)'}
    ),
    rce('br', {}),
    rce('input',
        {type: 'submit', name: 'searchsubmit', value: 'Submit Search Query'}
    )
)

//Table

//Footer
const footer = rce('footer', 
    {className: 'stvctrtxt'},
    rce('a', {href: 'https://github.com/Sparen/Spn-ThreatViz'}, "Source Code (GitHub)")
)

//Main container for the application
const container = rce('div', 
    {},
    banner,
    subbanner,
    searchbar,
    footer
)

//Render the container
ReactDOM.render(
    container,
    document.getElementById('app')
)




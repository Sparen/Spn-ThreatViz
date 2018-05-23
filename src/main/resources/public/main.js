//Tutorial used: https://reactarmory.com/guides/learn-react-by-itself/react-basics

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

//Search Bar and text input
const searchbar = rce('div', 
    {className: 'stvctrtxt'},
    rce('input',
        {type: 'text', name: 'searchbar', className: 'stvsearchbar', placeholder: 'Input Search Query here (space-delimited)', id: 'searchfield'}
    ),
    rce('br', {}),
    rce('input',
        {type: 'submit', name: 'searchsubmit', value: 'Submit Search Query', onClick: runSearch} //runSearch must be passed without () since we want to defer execution
    )
)

//Debug Component for /test api call
class TestMessage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            output: ""
        };
    }

    componentDidMount() {
    fetch("/api/stv/test")
        .then(res => res.json())
        .then(
            (result) => {
                this.setState({
                    isLoaded: true,
                    output: result
                });
            },
            (error) => {
                this.setState({
                    isLoaded: true,
                    error
                });
            }
        )
    }

    render() {
        const { error, isLoaded, output } = this.state;
        if (error) {
            return rce('div', {className: 'stvctrtxt'}, "Error")
        } else if (!isLoaded) {
            return rce('div', {className: 'stvctrtxt'}, "Loading...")
        } else {
            return rce('div', {className: 'stvctrtxt'}, output)
        }
    }
}

//Table
//Table Component. Takes in the pure JSON output and creates tables
function OutputTable(props) {
    return rce(
        'table', {}
    )
}

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
    rce(TestMessage, {}),
    footer
)

//Render the container
ReactDOM.render(
    container,
    document.getElementById('app')
)

//API Calls
function runSearch() {
    //First, get the contents of the search bar
    var searchbarcontents = document.getElementById("searchfield").value;
    //alert("Search Field is: " + searchbarcontents);
}


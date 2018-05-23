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

//Search + Table Component. Takes in the pure JSON output and creates tables
class SearchComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            output: ""
        };
        this.runSearch = this.runSearch.bind(this);
    }

    componentDidMount() {
    }

    render() {
        const { error, isLoaded, output } = this.state;
        if (error) {
            return (
                rce('div', 
                    {className: 'stvctrtxt'},
                    rce('input',
                        {type: 'text', name: 'searchbar', className: 'stvsearchbar', placeholder: 'Input Search Query here (space-delimited)', id: 'searchfield'}
                    ),
                    rce('br', {}),
                    rce('input',
                        {type: 'submit', name: 'searchsubmit', value: 'Submit Search Query', onClick: this.runSearch} //runSearch must be passed without () since we want to defer execution
                    ),
                    rce('br', {}),
                    rce('p', {}, "There was an error with the search.")
                )
            )
        } else if (!isLoaded) {
            return (
                rce('div', 
                    {className: 'stvctrtxt'},
                    rce('input',
                        {type: 'text', name: 'searchbar', className: 'stvsearchbar', placeholder: 'Input Search Query here (space-delimited)', id: 'searchfield'}
                    ),
                    rce('br', {}),
                    rce('input',
                        {type: 'submit', name: 'searchsubmit', value: 'Submit Search Query', onClick: this.runSearch} //runSearch must be passed without () since we want to defer execution
                    ),
                    rce('br', {}),
                    rce('p', {}, "Please type in your search terms in the input field and press Submit.")
                )
            )
        } else {
            //First, generate and format all the rows and contents, and create the table element to insert.
            //Header: Vendor, Product name, Attack Vector, Attack Complexity, Base Score, Base Severity, Publish Date, Last Mod Date
            var header = rce('thead', {},
                rce('tr', {className: 'tablerow'},
                    rce('th', {className: 'tableelement'}, "Vendor"),
                    rce('th', {className: 'tableelement'}, "Product Name"),
                    rce('th', {className: 'tableelement'}, "Attack Vector"),
                    rce('th', {className: 'tableelement'}, "Attack Complexity"),
                    rce('th', {className: 'tableelement'}, "Base Score"),
                    rce('th', {className: 'tableelement'}, "Base Severity"),
                    rce('th', {className: 'tableelement'}, "Publish Date"),
                    rce('th', {className: 'tableelement'}, "Last Modification Date")
                )
            );
            var rows = [];
            for (var i = 0; i < output.length; i += 1) {
                var currthreat = output[i];
                var vendor = currthreat.vendor;
                var productName = currthreat.productName;
                var versionValues = currthreat.versionValues;
                var description = currthreat.description;
                var attackVector = currthreat.attackVector;
                var attackComplexity = currthreat.attackComplexity;
                var privilegesRequired = currthreat.privilegesRequired;
                var userInteraction = currthreat.userInteraction;
                var confidentialityImpact = currthreat.confidentialityImpact;
                var integrityImpact = currthreat.integrityImpact;
                var availabilityImpact = currthreat.availabilityImpact;
                var baseScore = currthreat.baseScore;
                var baseSeverity = currthreat.baseSeverity;
                var exploitabilityScore = currthreat.exploitabilityScore;
                var impactScore = currthreat.impactScore;
                var publishDate = currthreat.publishDate;
                var lastModifiedDate = currthreat.lastModifiedDate;
                //First row for this entry
                var rowobj1 = rce('tr', {className: 'tablerow'},
                    rce('td', {className: 'tableelement'}, vendor),
                    rce('td', {className: 'tableelement'}, productName),
                    rce('td', {className: 'tableelement'}, attackVector),
                    rce('td', {className: 'tableelement'}, attackComplexity),
                    rce('td', {className: 'tableelement'}, baseScore),
                    rce('td', {className: 'tableelement'}, baseSeverity),
                    rce('td', {className: 'tableelement'}, publishDate),
                    rce('td', {className: 'tableelement'}, lastModifiedDate)
                );
                rows.push(rowobj1);
                //Format version string
                var vstring = "";
                for (var j = 0; j < versionValues.length; j += 1) {
                    vstring += versionValues[j];
                    if (j != versionValues.length - 1) {
                        vstring += "; ";
                    }
                }
                var rowobj2 = rce('tr', {className: 'tablerow'},
                    rce('td', {className: 'tableelement', colSpan: '8'}, 
                        rce('p', {}, "Versions affected: " + vstring),
                        rce('p', {}, "Privileges Required: " + privilegesRequired + "; User Interaction Required: " + userInteraction),
                        rce('p', {}, "Confidentiality Impact: " + confidentialityImpact + "; Integrity Impact: " + integrityImpact + "; Availability Impact: " + availabilityImpact),
                        rce('p', {}, "Exploitability Score: " + exploitabilityScore + "; Impact Score: " + impactScore),
                        rce('p', {}, "Description: " + description)
                    )
                );
                rows.push(rowobj2);
            }
            var tabletoinsert = rce('table', {className: 'stvtable'},
                header,
                rce('tbody', {}, rows)
            );
            return (
                rce('div', 
                    {className: 'stvctrtxt'},
                    rce('input',
                        {type: 'text', name: 'searchbar', className: 'stvsearchbar', placeholder: 'Input Search Query here (space-delimited)', id: 'searchfield'}
                    ),
                    rce('br', {}),
                    rce('input',
                        {type: 'submit', name: 'searchsubmit', value: 'Submit Search Query', onClick: this.runSearch} //runSearch must be passed without () since we want to defer execution
                    ),
                    rce('br', {}),
                    //Generated table goes here
                    tabletoinsert
                )
            )
        }
    }

    runSearch() {
        //First, get the contents of the search bar
        var searchbarcontents = document.getElementById("searchfield").value;
        //alert("Search Field is: " + searchbarcontents);
        fetch("/api/stv/search/" + searchbarcontents)
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
}

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
    rce(SearchComponent, {}),
    footer
)

//Render the container
ReactDOM.render(
    container,
    document.getElementById('app')
)

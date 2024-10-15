import express from "express";
import cors from "cors";
import fs from "fs";

const app = express();
app.use(cors());

const port = 3001;

const getCurrentNumItems = () => {
  const stringContent = fs.readFileSync("./database/items.json", {
    encoding: "utf-8",
  });
  const jsonContent = JSON.parse(stringContent);
  return jsonContent.numItems;
};

const updateCurrentNumItems = (newNumItems) => {
  fs.writeFileSync(
    "./database/items.json",
    JSON.stringify({
      numItems: newNumItems,
    })
  );
};

app.get("/getData", (req, res) => {
  console.log("getData called");
  res.send({ numItems: getCurrentNumItems() });
});

app.post("/decrease", (req, res) => {
  console.log("decrease called");

  const currentItems = getCurrentNumItems();
  if (currentItems > 0) {
    const newNumItems = currentItems - 1;
    updateCurrentNumItems(newNumItems);

    res.send({ numItems: newNumItems });
  } else {
    res.status(500).send({ message: "number of items cannot be less than 0" });
  }
});

app.post("/increase", (req, res) => {
  const currentItems = getCurrentNumItems();
  if (currentItems < 2) {
    const newNumItems = currentItems + 1;
    updateCurrentNumItems(newNumItems);

    res.send({ numItems: newNumItems });
  } else {
    res.status(500).send({ message: "number of items cannot be more than 2" });
  }
});

app.listen(port, () => {
  console.log("Express App Is Up and running at port ", port, "...");
});

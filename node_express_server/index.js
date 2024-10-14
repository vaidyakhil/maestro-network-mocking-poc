import express from "express";
import cors from "cors";

const app = express();
app.use(cors());

const port = 3001;

app.get("/getData", (req, res) => {
  console.log("getData called");
  res.send({ numItems: 1 });
});

app.post("/decrease", (req, res) => {
  console.log("decrease called");
  res.send({ numItems: 0 });
  //   res.status(500).send({ message: "atleast 1 item has to be there" });
});

app.post("/increase", (req, res) => {
  console.log("increase called");
  res.send({ numItems: 2 });
  //   res.status(500).send({ message: "cannot be more than 2" });
});

app.listen(port, () => {
  console.log("Express App Is Up and running at port ", port, "...");
});

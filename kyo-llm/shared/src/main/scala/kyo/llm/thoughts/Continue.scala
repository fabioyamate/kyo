package kyo.llm.thoughts

import kyo.llm._

case class Continue(
    `Always continue the conversation`: Boolean,
    `Don't thank the user`: Boolean
) extends Thought
